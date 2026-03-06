package command

import (
	"github.com/TypingHare/web.carton/v2026/web/web/share"
	"github.com/spf13/cobra"
)

func AddCommand(d share.WebDecorationLike) *cobra.Command {
	command := &cobra.Command{
		Use:   "add <name> <url>",
		Short: "Add a web record",
		Args:  cobra.ExactArgs(2),
		RunE: func(cmd *cobra.Command, args []string) error {
			cabinet, err := share.GetWebCabinet(d.Chamber())
			if err != nil {
				cmd.PrintErrf("failed to get web cabinet: %v\n", err)
				return nil
			}

			name := args[0]
			url := args[1]
			webRecord := share.NewWebRecord(name, url)
			cabinet.Objects = append(cabinet.Objects, webRecord)

			return nil
		},
	}

	return command
}
